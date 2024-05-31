package app.study.register;

import app.util.ModPersistData;
import app.util.ValidateControlFx;

public class StudyRegisterService {

    public void validateFields(StudyRegisterComponentsFxDto componentsFxDto) throws Exception {
        ValidateControlFx validate = new ValidateControlFx();
        validate.setControl(componentsFxDto.getTxtMatter());
        validate.setError(false);
        validate.validateControl();
        if (validate.getError()) {
            throw new Exception("Campo Assunto invalido.");
        }
    }

    public void extractFields(StudyRegisterComponentsFxDto componentsFxDto, Study study) {
        study.setMatter(componentsFxDto.getTxtMatter().getText());
    }

    public void executePersistence(Study study, ModPersistData modPersistData) throws Exception {
        StudyDao studyDao = new StudyDao();

        switch (modPersistData) {
            case INSERT: {
                try {
                    studyDao.save(study);
                } catch (Exception e) {
                    throw new Exception("Falha ao salvar o registro.");
                }
                break;
            }

            case UPDATE: {
                try {
                    studyDao.update(study);
                } catch (Exception e) {
                    throw new Exception("Falha ao atualizar o registro.");
                }
                break;
            }

            case DELETE: {
                try {
                    studyDao.delete(study);
                } catch (Exception e) {
                    throw new Exception("Falha ao remover registro.");
                }
                break;
            }

            default:
                throw new IllegalStateException("Unexpected value: " + modPersistData);
        }
    }

    public void clearScreen(StudyRegisterComponentsFxDto componentsFxDto) {
        componentsFxDto.getTxtMatter().clear();
        componentsFxDto.getMsgUser().setText("");
    }

    public void showStudyScreen(StudyRegisterComponentsFxDto componentsFxDto, Study study) {
        componentsFxDto.getTxtMatter().setText(study.getMatter());
        componentsFxDto.getMsgUser().setText("");
    }
}
