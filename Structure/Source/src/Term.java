
public class Term {

	private String name;
	private String transcription;
	private String description;
	
	public Term(String name, String transcription, String description) {
		
		this.name = name;
		this.transcription = transcription;
		this.description = description;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTranscription() {
		return transcription;
	}

	public void setTranscription(String transcription) {
		this.transcription = transcription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Term [name=" + name + ", transcription=" + transcription + ", description=" + description + "]";
	}
	
}
