using System;

namespace Yakitori.Core.Plugin
{
    [AttributeUsage(AttributeTargets.Assembly)]
    class YakitoriPluginClass : Attribute
    {
        public readonly string PluginClass;

        public YakitoriPluginClass(string clazz)
        {
            PluginClass = clazz;
        }
    }
}
