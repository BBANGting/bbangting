//package com.khu.bbangting.domain.image.service;
//
//import lombok.extern.java.Log;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.UUID;
//
//@Service
//@Log
//public class FileService {
//
//    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
//        UUID uuid = UUID.randomUUID();
//        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
//        String savedFileName = uuid.toString() + extension;
//
//        // 디렉토리 경로 생성
//        File directory = new File(uploadPath);
//
//        // 디렉토리가 없으면 생성
//        if (!directory.exists()) {
//            if (directory.mkdirs()) {
//                System.out.println("디렉토리 생성 성공");
//            } else {
//                System.out.println("디렉토리 생성 실패");
//            }
//        }
//
//        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
//        try (FileOutputStream fos = new FileOutputStream(fileUploadFullUrl)) {
//            fos.write(fileData);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return savedFileName;
//    }
//
//    public void deleteFile(String filePath) throws Exception {
//        File deleteFile = new File(filePath);
//
//        if(deleteFile.exists()) {
//            deleteFile.delete();
//            log.info("파일을 삭제하였습니다.");
//        } else {
//            log.info("파일이 존재하지 않습니다.");
//        }
//    }
//}