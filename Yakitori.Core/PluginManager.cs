using System;
using System.Collections.Generic;
using System.IO;
using System.Reflection;
namespace Yakitori.Core
{
	public class PluginManager
	{
		private readonly DirectoryInfo PluginDirectory;
        private Dictionary<string, IPlugin> plugins = new Dictionary<string, IPlugin>();

		public PluginManager()
		{
			var current = Environment.CurrentDirectory;
			var dir = System.IO.Path.Combine(current, "plugins");
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
				var asm = Assembly.LoadFrom(file.FullName);
                LoadPlugin(asm);
			}
		}

        private void LoadPlugin(Assembly asm)
        {
            foreach (var type in asm.GetTypes())
            {
                if (type.IsInterface)
                {
                    continue;
                }
                if (type.GetInterface("Yakitori.Core.IPlugin") != null)
                {
                    IPlugin plugin = (IPlugin) Activator.CreateInstance(type);
                    string id = plugin.PluginID;
                    plugins.Add(id, plugin);
                }
            }
        }

        public IPlugin GetPlugin(string id)
        {
            if (plugins.ContainsKey(id))
            {
                return plugins[id];
            }
            return null;
        }
    }
}

