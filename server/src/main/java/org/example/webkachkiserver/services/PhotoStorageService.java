package org.example.webkachkiserver.services;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.example.webkachkiserver.models.course.Course;
import org.example.webkachkiserver.models.user.TYPE;
import org.example.webkachkiserver.models.user.User;
import org.example.webkachkiserver.repositrories.CourseRepository;
import org.example.webkachkiserver.repositrories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class PhotoStorageService {

    @Autowired
    private Storage storage;

    @Autowired
    private UserRepository userRepository;

    @Value("${gcs.bucket.photo.name}")
    private String bucketName;
    @Autowired
    private CourseRepository courseRepository;

    public String uploadUserProfilePhoto(MultipartFile file, long userId) throws IOException {
        String blobName = "photos/user/" + userId + "/" + file.getOriginalFilename();
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, blobName)
                .setContentType(file.getContentType())
                .build();
        storage.create(blobInfo, file.getBytes());

        return blobName;
    }

    public String getSignedUrl(String objectName) {
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, objectName).build();
        URL signedUrl = storage.signUrl(
                blobInfo,
                120, TimeUnit.HOURS,
                Storage.SignUrlOption.withV4Signature()
        );
        return signedUrl.toString();
    }

    public User getUser(long userId) {
        User user = userRepository.findById(userId);
        String signedUrl = getSignedUrl(user.getPathToPhoto());
        user.setPathToPhoto(signedUrl);
        return user;
    }

    public ResponseEntity<?> getUrl(long lessonId) {
        return ResponseEntity.ok(userRepository.findById(lessonId).getPathToPhoto());
    }

    public ResponseEntity<?> getPhotosForIndex() {
        Map<String, String> photoUrls = new HashMap<>();
        Page<Blob> blobsStandard =
                storage.list(
                        bucketName,
                        Storage.BlobListOption.prefix("photos/index/")
                );

        for (Blob blob : blobsStandard.iterateAll()) {
            String url = getSignedUrl(blob.getName());
            String name = blob.getName().substring(13);
            photoUrls.put(name, url);
        }
        photoUrls.putAll(getPhotosForCourses());
        photoUrls.putAll(getPhotosForTrainers());
        return ResponseEntity.ok(photoUrls);
    }
    public Map<String, String> getPhotosForTrainers() {
        Map<String, String> photoUrls = new HashMap<>();
        List<User> users = userRepository.findAllByType(TYPE.COACH);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            String url = user.getPathToPhoto();
            photoUrls.put(user.getPathToPhoto() + i + 1, url);
        }
        return photoUrls;
    }
    public Map<String, String> getPhotosForCourses() {
        Map<String, String> photoUrls = new HashMap<>();
        List<Course> courses = courseRepository.findAll();
        courses.sort((c1, c2) -> Double.compare(c2.getRates(), c1.getRates()));
        courses.stream()
                .limit(3)
                .toList();
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            String url = course.getPathToPreviewPhoto();
            photoUrls.put(course.getPathToPreviewPhoto() + i + 1, url);
        }
        return photoUrls;
    }
    public ResponseEntity<?> getMiscPhotos() {
        Map<String, String> photoUrls = new HashMap<>();
        Page<Blob> blobsStandard =
                storage.list(
                        bucketName,
                        Storage.BlobListOption.prefix("photos/misc/")
                );

        for (Blob blob : blobsStandard.iterateAll()) {
            String url = getSignedUrl(blob.getName());
            String name = blob.getName().substring(12);
            photoUrls.put(name, url);
        }
        return ResponseEntity.ok(photoUrls);
    }
}
