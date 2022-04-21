package com.fandom.util;

import com.dropbox.core.*;
import com.dropbox.core.oauth.DbxCredential;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.users.FullAccount;
import com.fandom.model.Media;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class DropboxUtils {

    private static final String ACCESS_TOKEN = "sl.BFnNllbMKX7Fdej7pChungHcpNMOGxq3d2KVnzh4YPZyKNawKNYZriTNMC4Zefs1eMVl20hTVUV_LXhDWLf-nPFqohgOWt7eDCGTxuFAxzXd8sJGRgcltYZBMLiNgC6l4W1nsODK";
    private static final String REFRESH_TOKEN = "X1Zvicvuq20AAAAAAAAAAUh7kTMPFLii3A9W9KpzlJ_cQreCkAVOshOv1HeryiFK";
    private static final String dropboxAppKey = "s5a5h1sjt52m5rz";
    private static final String dropboxAppSecret = "ctksy3w6zmfd3n8";
    private static DbxClientV2 client;

    public DropboxUtils() throws DbxApiException, DbxException, IOException {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("fandom").build();
        DbxCredential credentials = new DbxCredential(ACCESS_TOKEN, -1L,REFRESH_TOKEN, dropboxAppKey, dropboxAppSecret);
        DropboxUtils.client = new DbxClientV2(config, credentials);
        FullAccount account = client.users().getCurrentAccount();
    }

    public static Media uploadFile(InputStream in, String fileName) throws UploadErrorException, DbxException, IOException{
        String name = fileName.replaceAll("\\s+", "_") +"_"+System.currentTimeMillis()/100;
        String path = "/" + name;
        FileMetadata metadata = DropboxUtils.client.files().uploadBuilder(path).uploadAndFinish(in);

        SharedLinkMetadata sharedLink = client.sharing().createSharedLinkWithSettings(path);
        String url = convertToRAWLink(sharedLink.getUrl());
        return new Media(url, name, path);
    }

    public static void getFiles() throws ListFolderErrorException, DbxException{
        ListFolderResult result = client.files().listFolder("");
        //client.files().deleteV2("/")
        while (true){
            for(Metadata metadata : result.getEntries()){
                System.out.println(metadata.getPathLower());
            }
            if(!result.getHasMore()){
                System.out.println("There is no more file!");
                break;
            }

            result = client.files().listFolderContinue(result.getCursor());
        }
    }

    public static String convertToRAWLink(String sharedLink){
        String rawLink = sharedLink.substring(0, sharedLink.length()-4)+"raw=1";
        return rawLink;
    }
}
