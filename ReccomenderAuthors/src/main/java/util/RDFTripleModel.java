package util;

public class RDFTripleModel {

	private String Subject;
	
	private String Predicate;
	
	private String Object;

	public RDFTripleModel(String subject, String predicate, String object) {
		super();
		Subject = subject;
		Predicate = predicate;
		Object = object;
	}
	
	public RDFTripleModel()
	{
		super();
	}

	public String getSubject() {
		return this.Subject;
	}

	public void setSubject(String subject) {
		this.Subject = subject;
	}

	public String getPredicate() {
		return this.Predicate;
	}

	public void setPredicate(String predicate) {
		this.Predicate = predicate;
	}

	public String getObject() {
		return this.Object;
	}

	public void setObject(String object) {
		this.Object = object;
	}
	
	@Override
	public String toString()
	{
		if(this.Object.startsWith("http://") || this.Object.startsWith("https://"))
		{
			return "<" + this.Subject + "> <" + this.Predicate + "> <" + this.Object + ">";
		}
		else
		{
			return "<" + this.Subject + "> <" + this.Predicate + "> '" + this.Object.replace("'","\u2019") + "'";
		}
	}
	
	@Override
	public boolean equals(Object m2)
	{
		if(m2 == null)
			return false;
		if((m2 instanceof RDFTripleModel) == false)
			return false;
		
		return this.getObject().equals(((RDFTripleModel)m2).getObject()) && this.getPredicate().equals(((RDFTripleModel)m2).getPredicate())
				&& this.getSubject().equals(((RDFTripleModel)m2).getSubject());
		
	}
}
