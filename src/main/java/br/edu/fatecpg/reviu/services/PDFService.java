package br.edu.fatecpg.reviu.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PDFService {

    public String extractText(MultipartFile file) {
        // Tenta abrir o arquivo PDF
        try (PDDocument document = PDDocument.load(file.getInputStream())) {

            // Instancia o "removedor" de texto
            PDFTextStripper stripper = new PDFTextStripper();

            // Extrai todo o texto do documento
            String text = stripper.getText(document);

            if (text.trim().isEmpty()) {
                throw new RuntimeException("O PDF está vazio ou é uma imagem (não tem texto selecionável).");
            }

            return text;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o PDF: " + e.getMessage());
        }
    }
}