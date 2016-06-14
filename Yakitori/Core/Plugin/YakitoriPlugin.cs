namespace Yakitori.Core.Plugin
{
    public abstract class YakitoriPlugin
    {
        private bool _enabled = false;

        public bool enabled
        {
            get
            {
                return _enabled;
            }
            set
            {
                if (value)
                {
                    OnEnable();
                }
                else
                {
                    OnDisable();
                }
                _enabled = value;
            }
        }

        private void OnEnable() {}

        private void OnDisable() {}
    }
}
