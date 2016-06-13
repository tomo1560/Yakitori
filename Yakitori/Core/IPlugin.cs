namespace Yakitori.Core
{
    public interface IPlugin
    {
        string PluginID { get; }

        void OnEnable();
        void OnDisable();
    }
}
