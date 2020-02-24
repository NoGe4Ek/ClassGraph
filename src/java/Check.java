public class Check {
    Check(Object attr1){
        this.attr1 = attr1;
    }

    Object attr1;

    public boolean isValid() {
        return attr1 != null;
    }
}
