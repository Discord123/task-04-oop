package xml;

public class Document {

    private String information;

    public Document(String information) {
        this.information = information;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        return information != null ? information.equals(document.information) : document.information == null;
    }

    @Override
    public int hashCode() {
        return information != null ? information.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Document{");
        sb.append("information='").append(information).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
