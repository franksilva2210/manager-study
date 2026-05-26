package app.ui.document.edit;

import app.application.document.DocumentDTO;
import app.application.document.DocumentMapper;
import app.domain.document.Document;
import app.infra.document.DocumentRepository;

public class ManagerTextService {

    private DocumentRepository documentRepository = new DocumentRepository();

    public DocumentDTO save(DocumentDTO documentDto) {
        if (documentDto.getId() != null && documentDto.getId() > 0) {
            return DocumentMapper.toDTO(documentRepository.update(documentDto));
        } else {
            Document document = DocumentMapper.toEntity(documentDto);
            return DocumentMapper.toDTO(documentRepository.save(document));
        }
    }

    public void remove(Long id) {
        documentRepository.delete(id);
    }

}
