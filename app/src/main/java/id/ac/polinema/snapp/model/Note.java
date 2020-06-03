package id.ac.polinema.snapp.model;

public class Note {
    private String title;
    private String content;
    private String noteDate;
//    private String tag;

    public Note() {
    }

    public Note(String title, String content, String noteDate) {
        this.title = title;
        this.content = content;
        this.noteDate = noteDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String ndate) {
        this.noteDate = ndate;
    }
}
