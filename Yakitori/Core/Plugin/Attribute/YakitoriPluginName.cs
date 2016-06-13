using System;

namespace Yakitori.Core.Plugin.Attribute
{
    [AttributeUsage(AttributeTargets.Assembly)]
    class YakitoriPluginName : System.Attribute
    {
        public readonly string Name;

        public YakitoriPluginName(string name)
        {
            Name = name;
        }
    }
}
