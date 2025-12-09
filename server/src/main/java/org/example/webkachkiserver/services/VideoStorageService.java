package org.example.webkachkiserver.services;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.HttpMethod;
import com.google.cloud.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.example.webkachkiserver.models.lesson.Lesson;
import org.example.webkachkiserver.repositrories.CourseRepository;
import org.example.webkachkiserver.repositrories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class VideoStorageService {

    @Autowired
    private Storage storage;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Value("${gcs.bucket.video.name}")
    private String bucketName;

    public String generateUrl(String type, long courseId, long lessonId) {
        log.info("Storage credentials: {}", storage.getOptions().getCredentials());
        String blobName = "videos/course" + courseId + "/" + "lesson" + lessonId;
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, blobName)
                .setContentType(type)
                .build();
        Map<String, String> params = new HashMap<>();
        params.put("uploadType", "resumable");
        URL signedUrl = storage.getOptions()
                        .getService()
                .signUrl(blobInfo, 15, TimeUnit.MINUTES, Storage.SignUrlOption.httpMethod(HttpMethod.PUT),
                        Storage.SignUrlOption.withV4Signature(),
                        Storage.SignUrlOption.withContentType());
        return signedUrl.toString();
    }
    public String generateUrl(String type, long courseId) {
        log.info("Storage credentials: {}", storage.getOptions().getCredentials());
        String name = "preview_" + courseId + ".mp4";
        String blobName = "videos/course" + courseId + "/" + name;
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, blobName)
                .setContentType(type)
                .build();
        Map<String, String> params = new HashMap<>();
        params.put("uploadType", "resumable");
        URL signedUrl = storage.getOptions()
                .getService()
                .signUrl(blobInfo, 15, TimeUnit.MINUTES, Storage.SignUrlOption.httpMethod(HttpMethod.PUT),
                        Storage.SignUrlOption.withV4Signature(),
                        Storage.SignUrlOption.withContentType());
        return signedUrl.toString();
    }
    public String generateUrl(String type, int shortsId) {
        log.info("Storage credentials: {}", storage.getOptions().getCredentials());
        String name = shortsId + ".mp4";
        String blobName = "videos/shorts" + name;
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, blobName)
                .setContentType(type)
                .build();
        Map<String, String> params = new HashMap<>();
        params.put("uploadType", "resumable");
        URL signedUrl = storage.getOptions()
                .getService()
                .signUrl(blobInfo, 15, TimeUnit.MINUTES, Storage.SignUrlOption.httpMethod(HttpMethod.PUT),
                        Storage.SignUrlOption.withV4Signature(),
                        Storage.SignUrlOption.withContentType());
        return signedUrl.toString();
    }

    public String getSignedUrl(String objectName) {
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, objectName).build();
        URL signedUrl = storage.signUrl(
                blobInfo,
                15, TimeUnit.MINUTES,
                Storage.SignUrlOption.withV4Signature()
        );
        return signedUrl.toString();
    }
}
