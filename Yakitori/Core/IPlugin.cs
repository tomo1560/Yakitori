namespace Yakitori.Core
{
    public interface IPlugin
    {
        string PluginID { get; }

        bool OnEnable();
        bool OnDisable();
    }
}
