package com.devyam.springintegrationftpupload;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController

// for automatic constructor injection using lombok
@RequiredArgsConstructor
public class UploadRestController {

    private final UploadMessagingGateway gateway;

    @GetMapping("/api/upload/{content}")
    public String uploadFile(@PathVariable String content, @RequestParam String extra) throws IOException {
        String resultContent = String.format("Hello... We have uploaded with some extra... %s \nwith some extra stuff %s", content, extra);
        File file = new File("temp/mytempfile.txt");
        if (file.exists())
            file.delete();
        FileUtils.writeStringToFile(file, resultContent, "UTF-8");

        gateway.uploadFile(file);

        return resultContent;
    }
}
