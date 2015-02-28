package hudson.plugins.filesystem_scm.dsl;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import hudson.PluginWrapper;
import hudson.plugins.filesystem_scm.FSSCM;
import jenkins.model.Jenkins;

/**
 * Created by jeremymarshall on 25/02/2015.
 */

public class FSSCMConverter extends AbstractReflectionConverter {

    public FSSCMConverter(Mapper mapper, ReflectionProvider reflectionProvider){
        super(mapper, reflectionProvider);
    }

    public boolean canConvert(Class clazz)
    {
        return FSSCM.class.equals(clazz);
    }

    public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context) {
        writer.addAttribute("class", obj.getClass().getName());
        PluginWrapper p = Jenkins.getInstance().pluginManager.whichPlugin(obj.getClass());

        writer.addAttribute("plugin", p.getShortName() + "@" + p.getVersion());
        super.marshal(obj, writer, context);
    }

}

