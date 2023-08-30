/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ******************************************************************************/
package org.apache.sling.scripting.sightly.apps.anf__002d__code__002d__challenge.components.form.hidden.country;

import java.io.PrintWriter;
import java.util.Collection;
import javax.script.Bindings;

import org.apache.sling.scripting.sightly.render.RenderUnit;
import org.apache.sling.scripting.sightly.render.RenderContext;

public final class country_html extends RenderUnit {

    @Override
    protected final void render(PrintWriter out,
                                Bindings bindings,
                                Bindings arguments,
                                RenderContext renderContext) {
// Main Template Body -----------------------------------------------------------------------------

Object _global_abc = null;
Object _dynamic_properties = bindings.get("properties");
_global_abc = renderContext.call("use", com.anf.core.models.ValidateAge.class.getName(), obj());
out.write("\n");
{
    String var_0 = (((("<!-- <div class=\"country__cmp\">\n\tCountry:" + renderContext.getObjectModel().toString(renderContext.call("xss", renderContext.getObjectModel().resolveProperty(_dynamic_properties, "countries"), "comment"))) + "\n</div>\n<div data-sly-list=") + renderContext.getObjectModel().toString(renderContext.call("xss", renderContext.getObjectModel().resolveProperty(_global_abc, "message"), "comment"))) + ">\n</div> -->");
    out.write(renderContext.getObjectModel().toString(var_0));
}
out.write("\n<div class=\"country__cmp\">\n\n    Country:<input type=\"text\" id=\"country\" name=\"country\"");
{
    Object var_attrvalue1 = renderContext.getObjectModel().resolveProperty(_dynamic_properties, "countries");
    {
        Object var_attrcontent2 = renderContext.call("xss", var_attrvalue1, "attribute");
        {
            Object var_shoulddisplayattr4 = ((renderContext.getObjectModel().toBoolean(var_attrcontent2) ? var_attrcontent2 : ("false".equals(var_attrvalue1))));
            if (renderContext.getObjectModel().toBoolean(var_shoulddisplayattr4)) {
                out.write(" value");
                {
                    boolean var_istrueattr3 = (var_attrvalue1.equals(true));
                    if (!var_istrueattr3) {
                        out.write("=\"");
                        out.write(renderContext.getObjectModel().toString(var_attrcontent2));
                        out.write("\"");
                    }
                }
            }
        }
    }
}
out.write(" readonly/><br/><br/>\n\n</div>");


// End Of Main Template Body ----------------------------------------------------------------------
    }



    {
//Sub-Templates Initialization --------------------------------------------------------------------



//End of Sub-Templates Initialization -------------------------------------------------------------
    }

}

