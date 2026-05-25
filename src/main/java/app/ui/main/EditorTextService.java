package app.ui.main;

import app.application.text.TextDTO;
import app.application.text.TextMapper;
import app.domain.text.Text;
import app.infra.text.TextRepository;

public class EditorTextService {

    private TextRepository textRepository = new TextRepository();

    public TextDTO save(TextDTO textDto) {
        if (textDto.getId() != null && textDto.getId() > 0) {
            return TextMapper.toDTO(textRepository.update(textDto));
        } else {
            Text text = TextMapper.toEntity(textDto);
            return TextMapper.toDTO(textRepository.save(text));
        }
    }

}
