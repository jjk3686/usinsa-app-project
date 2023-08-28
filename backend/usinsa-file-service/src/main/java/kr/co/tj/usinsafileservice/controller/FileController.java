package kr.co.tj.usinsafileservice.controller;

import java.io.File;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.tj.usinsafileservice.dto.FileDTO;
import kr.co.tj.usinsafileservice.dto.FileEntity;
import kr.co.tj.usinsafileservice.repository.FileRepository;
import kr.co.tj.usinsafileservice.service.FileService;

@Controller
@RequestMapping("/file-service")
public class FileController {

	@Autowired
	private FileService fileService;

	@Autowired
	private FileRepository fileRepository;

//	@GetMapping("/image/{bid}")
//	public ResponseEntity<?> findByBId(@PathVariable("bid") Long bid) {
//	    // Blob 데이터의 byte 배열 가져오기
//	    byte[] fileBytes = fileService.getFileBytesByBId(bid);
//	    ByteArrayResource resource = new ByteArrayResource(fileBytes);
//
//	    HttpHeaders headers = new HttpHeaders();
//
//	    // 파일 확장자에 따라 적절한 MediaType 설정
//	    String fileName = "image.jpg"; // 파일 이름을 가져오거나, 데이터베이스에 확장자 정보가 있다면 활용
//	    String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
//
//	    if (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg")) {
//	        headers.setContentType(MediaType.IMAGE_JPEG);
//	    } else if (fileExtension.equalsIgnoreCase("png")) {
//	        headers.setContentType(MediaType.IMAGE_PNG);
//	    } else if (fileExtension.equalsIgnoreCase("gif")) {
//	        headers.setContentType(MediaType.IMAGE_GIF);
//	    } else {
//	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // 기타 파일 타입에 대한 처리
//	    }
//
////	    return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
//	    return ResponseEntity.ok().headers(headers).contentLength(fileBytes.length).body(resource);
//	}
	
	@GetMapping("/image/{bid}")
	public ResponseEntity<?> findByBId(@PathVariable("bid") Long bid) {
		 byte[] fileBytes = fileService.fintByBid(bid);
	      if (fileBytes != null) {
	         ByteArrayResource resource = new ByteArrayResource(fileBytes);
	         HttpHeaders headers = new HttpHeaders();
	         headers.setContentType(MediaType.IMAGE_JPEG);

	         return ResponseEntity.ok().headers(headers).contentLength(fileBytes.length).body(resource);
	      } else {
	         return ResponseEntity.notFound().build();
	      }
	   }



	
	@DeleteMapping("/filedelete")
	public ResponseEntity<?> filedelete(@RequestBody FileDTO fileDTO){
		Map<String, Object> map = new HashMap<>();
		
		fileService.delete(fileDTO.getBid());
		map.put("result", fileDTO);
		return ResponseEntity.ok().body(map);
		
	}
	
	@PostMapping("/fileupload")
	public ResponseEntity<?> fileupload(@RequestParam("file") MultipartFile file,
	                                    @RequestParam("bid") Long bid,
	                                    @RequestParam("uploaderId") String uploaderId) {
	    if (file.isEmpty()) {
	        return ResponseEntity.badRequest().body("파일이 비어 있습니다.");
	    }

	    String orgFilename = file.getOriginalFilename();
	    // 파일이 저장될 경로 설정
	    File path = new File("D:" + File.separator + "workspace" + File.separator +
	            "workspace_flutter" + File.separator + "usinsaapp" + File.separator + "asset");

	    if (!path.exists()) {
	        path.mkdirs();
	    }

	    String datePath = FileService.makePath(path.getPath());
	    String savedName = FileService.makeFilename(orgFilename);

	    try {
	        byte[] fileBytes = file.getBytes(); // 파일 데이터를 byte 배열로 추출

	        // 파일을 저장하는 코드
	        FileUtils.writeByteArrayToFile(new File(path + datePath, savedName), fileBytes);

	        // 파일 정보를 DB에 저장하는 코드 추가
	        Date date = new Date();

	        System.out.println(savedName);

	        // 파일 정보를 DB에 저장하는 코드 추가
	        FileEntity fileEntity = new FileEntity();
	        fileEntity.setOriginalName(orgFilename);
	        fileEntity.setSavedName(savedName);
	        fileEntity.setUploadDate(date);
	        fileEntity.setUploaderId(uploaderId);
	        fileEntity.setBid(bid);

	        // 파일 데이터를 Base64로 인코딩하여 저장
	        String encodedFileData = Base64.getEncoder().encodeToString(fileBytes);
	        fileEntity.setFileBytes(encodedFileData.getBytes());

	        fileRepository.save(fileEntity); // 파일 정보를 DB에 저장합니다.

	        return ResponseEntity.ok().body("성공");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return ResponseEntity.badRequest().body("실패");
	}
	
//	@PostMapping("/fileupload/user")
//	public ResponseEntity<?> fileupload(@RequestParam("file") MultipartFile file,
//	                                    @RequestParam("uploaderId") String uploaderId) {
//	    if (file.isEmpty()) {
//	        return ResponseEntity.badRequest().body("파일이 비어 있습니다.");
//	    }
//
//	    String orgFilename = file.getOriginalFilename();
//	    // 파일이 저장될 경로 설정
//	    File path = new File("D:" + File.separator + "workspace" + File.separator +
//	            "workspace_flutter" + File.separator + "usinsaapp" + File.separator + "asset");
//
//	    if (!path.exists()) {
//	        path.mkdirs();
//	    }
//
//	    String datePath = FileService.makePath(path.getPath());
//	    String savedName = FileService.makeFilename(orgFilename);
//
//	    try {
//	        byte[] fileBytes = file.getBytes(); // 파일 데이터를 byte 배열로 추출
//
//	        // 파일을 저장하는 코드
//	        FileUtils.writeByteArrayToFile(new File(path + datePath, savedName), fileBytes);
//
//	        // 파일 정보를 DB에 저장하는 코드 추가
//	        Date date = new Date();
//
//	        System.out.println(savedName);
//
//	        // 파일 정보를 DB에 저장하는 코드 추가
//	        FileEntity fileEntity = new FileEntity();
//	        fileEntity.setOriginalName(orgFilename);
//	        fileEntity.setSavedName(savedName);
//	        fileEntity.setUploadDate(date);
//	        fileEntity.setUploaderId(uploaderId);
//
//	        // 파일 데이터를 Base64로 인코딩하여 저장
//	        String encodedFileData = Base64.getEncoder().encodeToString(fileBytes);
//	        fileEntity.setFileBytes(encodedFileData.getBytes());
//
//	        fileRepository.save(fileEntity); // 파일 정보를 DB에 저장합니다.
//
//	        return ResponseEntity.ok().body("성공");
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//	    return ResponseEntity.badRequest().body("실패");
//	}
//	
//	
//	@GetMapping("/image/{id}")
//	public ResponseEntity<?> findByUploaderId(@PathVariable("id") Long id) {
//		 byte[] fileBytes = fileService.findById(id);
//	      if (fileBytes != null) {
//	         ByteArrayResource resource = new ByteArrayResource(fileBytes);
//	         HttpHeaders headers = new HttpHeaders();
//	         headers.setContentType(MediaType.IMAGE_JPEG);
//
//	         return ResponseEntity.ok().headers(headers).contentLength(fileBytes.length).body(resource);
//	      } else {
//	         return ResponseEntity.notFound().build();
//	      }
//	   }
//	
//	@DeleteMapping("/filedelete/user")
//	public ResponseEntity<?> filedelete2(@RequestBody FileDTO fileDTO){
//		Map<String, Object> map = new HashMap<>();
//		
//		fileService.delete2(fileDTO.getId());
//		map.put("result", fileDTO);
//		return ResponseEntity.ok().body(map);
//		
//	}

}
