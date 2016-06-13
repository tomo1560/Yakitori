using System;

namespace Yakitori.Core.Plugin.Attribute
{
    [AttributeUsage(AttributeTargets.Assembly)]
    class YakitoriPluginClass : System.Attribute
    {
        public readonly string PluginClass;

        public YakitoriPluginClass(string clazz)
        {
            PluginClass = clazz;
        }
    }
}
