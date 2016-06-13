using System;
using System.Collections.Generic;
using System.Text;

namespace Yakitori.Core.Plugin
{
    public class PluginInfo
    {
        public readonly string MainClass;
        public readonly string PluginId;
        public readonly string PluginName;
        public readonly string Description;
        public readonly string Version;
        public readonly string Author;

        public PluginInfo(string main, string id, string name, string desc, string version, string author)
        {
            MainClass = main;
            PluginId = id;
            PluginName = name ?? "Unnamed";
            Description = desc ?? "";
            Version = version ?? "0.0.0";
            Author = author ?? "Unnamed";
        }
    }
}
