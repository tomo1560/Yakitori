using System;

namespace Yakitori.Core.Plugin.Attribute
{
    [AttributeUsage(AttributeTargets.Assembly)]
    class YakitoriPluginDescription : System.Attribute
    {
        public readonly string Description;

        public YakitoriPluginDescription(string desc)
        {
            Description = desc;
        }
    }
}
