using System;

namespace Yakitori.Core.Plugin.Attribute
{
    [AttributeUsage(AttributeTargets.Assembly)]
    class YakitoriPluginVersion : System.Attribute
    {
        public readonly string Version;

        public YakitoriPluginVersion(string version)
        {
            Version = version;
        }
    }
}
