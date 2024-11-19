//package com.project.farmhelper.testfirebase;
//
//
//import com.google.cloud.storage.Blob;
//import com.google.cloud.storage.Bucket;
//import com.google.firebase.cloud.StorageClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.multipart.MultipartFile;
//
//public class Test {
//
//    // application.yml(application.properties) 설정파일에 storage 주소 추가
//    @Value("${firebase.storage-url}")
//    private String firebaseStorageUrl;
//
//    // 파일 업로드
//    public String uploadFirebaseBucket(MultipartFile multipartFile, String fileName) {
//        Bucket bucket = StorageClient.getInstance().bucket(firebaseStorageUrl);
//
//        Blob blob = bucket.create(fileName,
//                multipartFile.getInputStream(), multipartFile.getContentType());
//
//        return blob.getMediaLink(); // 파이어베이스에 저장된 파일 url
//    }
//
//    // 파일 삭제
//    public void deleteFirebaseBucket(String key) {
//        Bucket bucket = StorageClient.getInstance().bucket(firebaseStorageUrl);
//
//        bucket.get(key).delete();
//    }
//
//}
