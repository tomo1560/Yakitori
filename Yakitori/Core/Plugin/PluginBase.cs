namespace Yakitori.Core.Plugin
{
    public abstract class PluginBase
    {
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
            }
        }

        public void SetDisable()
        {
            if (enabled)
            {
                OnDisable();
                _enabled = false;
            }
        }

        private void OnEnable() {}

        private void OnDisable() {}
    }
}
