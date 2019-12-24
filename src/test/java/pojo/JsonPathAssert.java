package pojo;

public class JsonPathAssert {
    //断言预期jsonPath的值
    private String value;
    
    //断言预期jsonPath的表达式
    private String expression;

    public JsonPathAssert() {
	super();
    }

    public JsonPathAssert(String value, String expression) {
	super();
	this.value = value;
	this.expression = expression;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
	return "JsonPathAssert [value=" + value + ", expression=" + expression + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((expression == null) ? 0 : expression.hashCode());
	result = prime * result + ((value == null) ? 0 : value.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	JsonPathAssert other = (JsonPathAssert) obj;
	if (expression == null) {
	    if (other.expression != null)
		return false;
	} else if (!expression.equals(other.expression))
	    return false;
	if (value == null) {
	    if (other.value != null)
		return false;
	} else if (!value.equals(other.value))
	    return false;
	return true;
    }
    

}
