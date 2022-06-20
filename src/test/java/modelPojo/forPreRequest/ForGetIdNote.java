package modelPojo.forPreRequest;


public class ForGetIdNote {
    private Integer id;
    private Object user;
    private Object team;
    private String text;
    private Long date;
    private Object owner;

    public ForGetIdNote(Integer id, Object user, Object team, String text, Long date, Object owner) {
        this.id = id;
        this.user = user;
        this.team = team;
        this.text = text;
        this.date = date;
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public Object getUser() {
        return user;
    }

    public Object getTeam() {
        return team;
    }

    public String getText() {
        return text;
    }

    public Long getDate() {
        return date;
    }

    public Object getOwner() {
        return owner;
    }
}
