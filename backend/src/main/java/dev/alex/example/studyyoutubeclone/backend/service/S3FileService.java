package dev.alex.example.studyyoutubeclone.backend.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3FileService implements FileService {

    public static final String BUCKET_NAME = "shlyakhtin-youtube-clone";
    private final AmazonS3Client amazonS3Client;

    @Override
    public String uploadFile(MultipartFile file) {

        var filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        var key = UUID.randomUUID().toString() + "." + filenameExtension;

        var metaData = new ObjectMetadata();
        metaData.setContentLength(file.getSize());
        metaData.setContentType(file.getContentType());

        try {
            amazonS3Client.putObject(BUCKET_NAME, key, file.getInputStream(), metaData);
        } catch (IOException e)  {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An Exception occurred while uploading the file");
        }

        amazonS3Client.setObjectAcl(BUCKET_NAME, key, CannedAccessControlList.PublicRead);

        return amazonS3Client.getResourceUrl(BUCKET_NAME, key);
    }
}
