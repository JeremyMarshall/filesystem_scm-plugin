package hudson.plugins.filesystem_scm.dsl;

import com.thoughtworks.xstream.XStream;
import hudson.Extension;
import hudson.model.Describable;
import hudson.scm.SCM;
import hudson.plugins.filesystem_scm.FSSCM;
import hudson.plugins.filesystem_scm.Wildcard;
import hudson.util.XStream2;
import org.jenkinsci.plugins.jobdsl.stub.DslClosureUnsupported;
import org.jenkinsci.plugins.jobdsl.stub.DslNoClosureClass;
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method;
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter;
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Scm;

import java.util.Collections;

/**
 * Created by jeremymarshall on 1/01/2015.
 */
@Extension
public class DslSCM extends Scm{
    @Override
    public String getName(){
        return "FSSCM";
    }

    @Override
    public String getDescription(){
        return "Add a File System SCM";
    }

    @Override
    public final boolean hasMethods(){
        return true;
    };


    //public FSSCM(String path, String localPath, boolean clearWorkspace, boolean copyHidden, boolean filterEnabled, String filterType, List<Wildcard> wildcards) {

    @Method(description="Add a filesystem SCM")
    public Object fsscm(@Parameter(description="Source path") String path) {
        return fsscm(path, null);
    }

    @Method(description="Add a filesystem SCM")
    public Object fsscm(@Parameter(description="Source path") String path,
                        @Parameter(description="Destination in workspace") String localPath) {
        Describable<SCM> ret =  new FSSCM(path, localPath, false, false, false, null, Collections.<Wildcard>emptyList());
        return ret;
    }

    @Method(description="Add a File System SCM with a closure", closureClass = DslSCMClosure.class)
    public Object fsscm(@Parameter(description="The closure") Object closure)
            throws DslClosureUnsupported, DslNoClosureClass, IllegalAccessException, InstantiationException
    {
        DslSCMClosure i = (DslSCMClosure) runClosure(closure, DslSCMClosure.class);
        Describable<SCM> ret = new FSSCM(i.getPath(), i.getLocalPath(), i.getClearWorkspace(), i.copyHidden, i.getFilterEnabled(), i.getFilterType(), i.getWildcards());
        return ret;
    }


    @Override
    public boolean xstreamAlias(XStream2 xstream) {
        //items are fine the way they come out
        xstream.alias("scm", FSSCM.class);

        xstream.registerConverter(new FSSCMConverter(xstream.getMapper(), xstream.getReflectionProvider()));
        return true;
    }

}