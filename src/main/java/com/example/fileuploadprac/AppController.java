package com.example.fileuploadprac;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AppController {

    private final DocumentRepository documentRepository;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Document> documentList = documentRepository.findAll();
        model.addAttribute("documentList", documentList);

        return "home";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("document") MultipartFile multipartFile,
                             RedirectAttributes redirectAttributes) throws IOException {

        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        Document document = Document.builder()
                .name(filename)
                .content(multipartFile.getBytes())
                .size(multipartFile.getSize())
                .build();

        documentRepository.save(document);
        redirectAttributes.addFlashAttribute("message", "파일이 성공적으로 업로드 되었습니다.");
        return "redirect:/";
    }


    @GetMapping("/download")
    public void downloadFile(@Param("id") Long id, HttpServletResponse response) throws Exception {
        Optional<Document> optionalDocument = documentRepository.findById(id);

        if (!optionalDocument.isPresent()) {
            throw new Exception("Could not find document with ID: " + id);
        }

        Document document = optionalDocument.get();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + document.getName());

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(document.getContent());
        outputStream.close();
    }
}
