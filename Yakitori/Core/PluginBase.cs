namespace Yakitori.Core
{
    public abstract class PluginBase
    {
        public abstract string PluginID { get; }
        public abstract string Name { get; }
        public abstract string Description { get; }
        public abstract string Version { get; }
        public abstract string Author { get; }

        private bool _enabled = false;

        public bool enabled
        {
            get
            {
                return _enabled;
            }
        }

        public void SetEnable()
        {
            if (!enabled)
            {
                OnEnable();
                _enabled = true;
                // ToDo: Logに有効化された旨を表示
            }
            else
            {
                // ToDo: Logに有効化に失敗した旨を表示
            }
        }

        public void SetDisable()
        {
            if (enabled)
            {
                OnDisable();
                _enabled = false;
                // ToDo: Logに無効化された旨を表示
            }
            else
            {
                // ToDo: Logに無効化に失敗した旨を表示
            }
        }

        private void OnEnable() {}

        private void OnDisable() {}
    }
}
