package control;

public abstract class SavedManager implements ISaveManager {
	public abstract String getSaveFilePath();

	public abstract Object getObjectToSave();

	@Override
	public void save() {
		MainApp.saveObject(getSaveFilePath(), getObjectToSave());
	}
}
