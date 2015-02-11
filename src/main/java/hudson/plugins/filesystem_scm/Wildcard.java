package hudson.plugins.filesystem_scm;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.util.FormValidation;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

/**
 * Created by jeremymarshall on 11/02/2015.
 */
public class Wildcard extends AbstractDescribableImpl<Wildcard> {

    String filter;

    @DataBoundConstructor
    public Wildcard(String filter) {
        super();
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<Wildcard> {

        public DescriptorImpl() {
            load();
        }

        @Override
        public String getDisplayName() {
            return "Wildcard";
        }

        public FormValidation doFilterCheck(@QueryParameter final String value) {
            if (null == value || value.trim().length() == 0) return FormValidation.ok();
            if (value.startsWith("/") || value.startsWith("\\") || value.matches("[a-zA-Z]:.*")) {
                return FormValidation.error("Pattern can't be an absolute path");
            } else {
                try {
                    SimpleAntWildcardFilter filter = new SimpleAntWildcardFilter(value);
                } catch (Exception e) {
                    return FormValidation.error(e, "Invalid wildcard pattern");
                }
            }
            return FormValidation.ok();
        }
    }
}
