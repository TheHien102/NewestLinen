package com.example.newestlinen.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UploadService {
    @Autowired
    Cloudinary cloudinary;

    public String uploadImg(String img) throws IOException {
        return (String) cloudinary.uploader().upload(img, ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true,
                "public_id", "Asset"
        )).get("secure_url");
    }

    public String uploadVid(String img) throws IOException {
        return (String) cloudinary.uploader().upload(img, ObjectUtils.asMap(
                "use_filename", true,
                "resource_type", "video",
                "unique_filename", false,
                "overwrite", true,
                "public_id", "Asset"
        )).get("secure_url");
    }
}
