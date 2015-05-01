package hudson.plugins.filesystem_scm.dsl;

import hudson.plugins.filesystem_scm.Wildcard;
import javaposse.jobdsl.dsl.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by jeremymarshall on 31/01/2015.
 */

public class DslSCMContext implements Context {

    String path;
    String localPath;
    boolean clearWorkspace = false;
    boolean copyHidden = false;
    boolean filterEnabled = false;
    List<Wildcard> wildcards = Collections.<Wildcard>emptyList();
    String filterType = "include";

    public void path(String path) {
        this.path = path;
    }

    public void localPath( String localPath) {
        this.localPath = localPath;
    }

    public void clearWorkspace( boolean clear) {
        this.clearWorkspace = clear;
    }

    public void copyHidden( boolean copyHidden) {
        this.copyHidden = copyHidden;
    }

    public void filter(String type, String... filters)  throws UnknownFilterType {
        filter(type, Arrays.asList(filters));
    }

    public void filter(String type, List<String> filters)  throws UnknownFilterType{
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
