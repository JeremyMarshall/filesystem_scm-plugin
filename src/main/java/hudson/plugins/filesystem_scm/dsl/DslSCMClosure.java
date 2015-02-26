package hudson.plugins.filesystem_scm.dsl;

import hudson.Extension;
import hudson.plugins.filesystem_scm.Wildcard;
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method;
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by jeremymarshall on 31/01/2015.
 */
@Extension
public class DslSCMClosure extends org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Closure{

    String path;
    String localPath;
    boolean clearWorkspace = false;
    boolean copyHidden = false;
    boolean filterEnabled = false;
    List<Wildcard> wildcards = Collections.<Wildcard>emptyList();
    String filterType = "include";

    @Override
    public String getName(){
        return "FSSCM closure";
    }

    @Override
    public String getDescription(){
        return "FSSCM scm closure";
    }

    @Override
    public final boolean hasMethods(){
        return true;
    };

    @Method(description="Set Path")
    public void path(@Parameter(description="The path to read from") String path) {
        this.path = path;
    }

    @Method(description="Set Local Path")
    public void localPath(@Parameter(description="The directory in the workspace to write to") String localPath) {
        this.localPath = localPath;
    }

    @Method(description="Clear workspace")
    public void clearWorkspace(@Parameter(description="Clear directory before copying") boolean clear) {
        this.clearWorkspace = clear;
    }

    @Method(description="Copy hidden files")
    public void copyHidden(@Parameter(description="Copy hidden files") boolean copyHidden) {
        this.copyHidden = copyHidden;
    }

    @Method(description="Filter files")
    public void filter(@Parameter(description="include or exclude") String type, @Parameter(description="vaargs of wildcards to add") String... filters)  throws UnknownFilterType {
        filter(type, Arrays.asList(filters));
    }

    @Method(description="Filter files")
    public void filter(@Parameter(description="include or exclude") String type, @Parameter(description="list of wildcards to add") List<String> filters)  throws UnknownFilterType{
        this.filterEnabled = true;
        if (type.equalsIgnoreCase("include")) {
            this.filterType = "include";
        } else if (type.equalsIgnoreCase("exclude")) {
            this.filterType = "exclude";
        } else {
            throw new UnknownFilterType(type);
        }
        this.filterEnabled = true;
        this.wildcards = new ArrayList<Wildcard>();
        for (String f: filters) {
            wildcards.add( new Wildcard(f));
        }
    }

    public String getPath(){
        return path;
    }

    public String getLocalPath(){
        return localPath;
    }

    public boolean getClearWorkspace(){
        return clearWorkspace;
    }

    public boolean getCopyHidden(){
        return copyHidden;
    }

    public boolean getFilterEnabled() {
        return filterEnabled;
    }

    public List<Wildcard> getWildcards() {
        return wildcards;
    }

    public String getFilterType() {
        return filterType;
    }

}
