package com.ocr.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

//@RestController
//public class OcrController {
//
//	@PostMapping("/api/ocr")
//
//	public String DoOCR(@RequestParam("DestinationLanguage") String destinationLanguage,
//			@RequestParam("Image") MultipartFile image) throws IOException {
//
//		
//		OcrModel request = new OcrModel();
//		request.setDestinationLanguage(destinationLanguage);
//		request.setImage(image);
//
//		ITesseract instance = new Tesseract();
//
//		try {
//			
//			BufferedImage in = ImageIO.read(convert(image));
//
//			BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);
//            
//			Graphics2D g = newImage.createGraphics();
//			g.drawImage(in, 0, 0, null);
//			g.dispose();
//            
//			instance.setLanguage(request.getDestinationLanguage());
//			instance.setDatapath("C:\\Users\\Kuruba Naresh Kumar\\OneDrive\\tessdata");
//
//			String result = instance.doOCR(newImage);
//
//			return result;
//
//		} catch (TesseractException | IOException e) {
//			System.err.println(e.getMessage());
//			return "Error while reading image";
//		}
//
//	}
//	
//	public static File convert(MultipartFile file) throws IOException {
//	    File convFile = new File(file.getOriginalFilename());
//	    convFile.createNewFile();
//	    FileOutputStream fos = new FileOutputStream(convFile);
//	    fos.write(file.getBytes());
//	    fos.close();
//	    return convFile;
//	}
	




@RestController
public class OcrController {

    @PostMapping("/api/ocr")
    public List<String> DoOCR(@RequestParam("DestinationLanguage") String destinationLanguage,
            @RequestParam("Images") List<MultipartFile> images) throws IOException {

        List<String> results = new ArrayList<>();

        ITesseract instance = new Tesseract();
        instance.setLanguage(destinationLanguage);
        instance.setDatapath("C:\\Users\\Kuruba Naresh Kumar\\OneDrive\\tessdata");

        try {
            for (MultipartFile image : images) {
                BufferedImage in = ImageIO.read(convert(image));
                BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);

                Graphics2D g = newImage.createGraphics();
                g.drawImage(in, 0, 0, null);
                g.dispose();

                String result = instance.doOCR(newImage);
                results.add(result);
            }

            return results;
        } catch (TesseractException | IOException e) {
            System.err.println(e.getMessage());
            results.add("Error while reading image");
            return results;
        }
    }

    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}



