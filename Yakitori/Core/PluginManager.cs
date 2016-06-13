using System;
using System.Collections.Generic;
using System.IO;
using System.Reflection;

namespace Yakitori.Core
{
	public class PluginManager
	{
        public static readonly PluginManager Instance = new PluginManager();

		private readonly DirectoryInfo PluginDirectory;
        private Dictionary<string, PluginBase> Plugins = new Dictionary<string, PluginBase>();

		private PluginManager()
		{
			var current = Environment.CurrentDirectory;
			var dir = Path.Combine(current, "plugins");
			if (Directory.Exists(dir))
			{
				PluginDirectory = new DirectoryInfo(dir);
			}
			else
			{
				PluginDirectory = Directory.CreateDirectory(dir);
			}
		}

		public void LoadPlugins()
		{
			foreach (var file in PluginDirectory.GetFiles("*.dll"))
			{
                LoadPlugin(file);
			}
		}

        private void LoadPlugin(FileInfo file)
        {
            var asm = Assembly.LoadFrom(file.FullName);
            foreach (var type in asm.GetTypes())
            {
                if (type.IsInterface)
                {
                    continue;
                }
                if (type.BaseType.FullName == "Yakitori.Core.PluginBase")
                {
                    PluginBase plugin = (PluginBase) Activator.CreateInstance(type);
                    string id = plugin.PluginID;
                    Plugins.Add(id, plugin);
                    plugin.SetEnable();
                }
            }
        }

        public PluginBase GetPlugin(string id)
        {
            if (Plugins.ContainsKey(id))
            {
                return Plugins[id];
            }
            return null;
        }
    }
}

