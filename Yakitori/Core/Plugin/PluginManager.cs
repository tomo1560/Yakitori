using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using Yakitori.Core.Plugin.Attribute;

namespace Yakitori.Core.Plugin
{
	public class PluginManager
	{
        public static readonly PluginManager Instance = new PluginManager();

		private readonly DirectoryInfo PluginDirectory;
        private IDictionary<string, PluginBase> Plugins = new Dictionary<string, PluginBase>();
        private IList<PluginInfo> PluginInfoList = new List<PluginInfo>();

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
            PluginInfo info = parseAttributes(asm);
            if (info == null)
            {
                // ToDo : 後々Logでどうのこうのしましょう
                Console.WriteLine(file.Name + "はYakitoriPluginではありません。");
                return;
            }
            PluginBase plugin = asm.CreateInstance(info.MainClass) as PluginBase;
            if (plugin != null)
            {
                Plugins.Add(info.PluginId, plugin);
                PluginInfoList.Add(info);
                plugin.SetEnable();
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

        public IList<PluginInfo> GetLoadedPlugins()
        {
            return new List<PluginInfo>(PluginInfoList);
        }

        private PluginInfo parseAttributes(Assembly asm)
        {
            var main = asm.GetCustomAttribute<YakitoriPluginClass>().PluginClass;
            var id = asm.GetCustomAttribute<YakitoriPluginID>().PluginID;
            if (main == null || id == null)
            {
                return null;
            }
            var name = asm.GetCustomAttribute<YakitoriPluginName>().Name;
            var desc = asm.GetCustomAttribute<YakitoriPluginDescription>().Description;
            var version = asm.GetCustomAttribute<YakitoriPluginVersion>().Version;
            var author = asm.GetCustomAttribute<YakitoriPluginAuthor>().Author;
            return new PluginInfo(main, id, name, desc, version, author);
        }
    }
}

