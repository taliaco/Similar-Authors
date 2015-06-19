package dal;

import java.util.Collection;


import util.RDFTripleModel;

public interface IRepository {

	public <T extends RDFTripleModel> boolean InsertTriple(T tripple);

	public boolean InsertTripleCollection ( Collection <? extends RDFTripleModel> collection);

	public <T extends RDFTripleModel> boolean DeleteTriple(T tripple);

	public boolean DeleteTripleCollection ( Collection <? extends RDFTripleModel> collection);

	public Collection<RDFTripleModel> GetTripples(String subject);

	public boolean ClearGraph ();

}
