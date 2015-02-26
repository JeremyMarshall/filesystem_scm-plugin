package hudson.plugins.filesystem_scm.dsl;

/**
 * Created by jeremymarshall on 17/02/2015.
 */
public class UnknownFilterType extends  Exception{

    public UnknownFilterType(String type){
        super(type);
    }
}
