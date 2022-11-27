package com.example.newestlinen.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {
    @Autowired
    Cloudinary cloudinary;

    public String uploadImg(String img) throws IOException {
        return (String) cloudinary.uploader().upload(img, ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true
        )).get("secure_url");
    }

    public void deleteImg(String url) throws IOException {
        List<String> words = List.of(url.split("/"));
        String publicId = words.get(words.size() - 1).split("\\.")[0];

        cloudinary.uploader().destroy(publicId,ObjectUtils.asMap("invalidate",true));
    }

    public String uploadVid(String img) throws IOException {
        return (String) cloudinary.uploader().upload(img, ObjectUtils.asMap(
                "use_filename", true,
                "resource_type", "video",
                "unique_filename", false,
                "overwrite", true
        )).get("secure_url");
    }
}
