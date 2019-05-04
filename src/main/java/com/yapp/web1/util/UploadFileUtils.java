package com.yapp.web1.util;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

//직접적으로 S3Util클래스의 메서드를 사용하는 클래스
public class UploadFileUtils {

    //파일이름(파일경로+uid+파일명)생성 후 S3Util이용해 파일 업로드하는 메서드
    public static String uploadFile(String uploadPath, String originalName, byte[] byteData) throws Exception{
        S3Util s3 = new S3Util();

        UUID uid = UUID.randomUUID();//랜덤의 uid생성

        String saveName = "/"+uid.toString()+"_"+originalName; //파일명중복방지를위해 파일명변경 : 파일명=/uid_원래파일명

        String savedPath = calcPath(uploadPath);

        String uploadedFileName = null;

        uploadedFileName = (savedPath)+saveName.replace(File.separatorChar,'/');

        s3.fileUpload(uploadPath+uploadedFileName, byteData);

        return (uploadedFileName).replace(File.separatorChar, '/');
    }

    private static String calcPath(String uploadePath){
        Calendar cal = Calendar.getInstance();

        String yearPath = File.separator+cal.get(Calendar.YEAR);

        String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);

        makeDir(uploadePath, yearPath, monthPath);

        return monthPath;
    }

    private static void makeDir(String uploadePath, String... paths){
        if(new File(paths[paths.length-1]).exists()){
            return;
        }
        for(String path : paths){
            File dirPath = new File(uploadePath+path);

            if(!dirPath.exists()){
                dirPath.mkdir();
            }
        }
    }
}
