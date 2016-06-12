using System;
using System.IO;
using System.Text;
using System.Security.Cryptography;

namespace Yakitori.Network
{
    public class Cryptographer
    {
        private static Random cRandom = new Random();
        private static string MD5Key = "string";
        private static string AESKey = "string";
        private static string RJ256Key = "string";

        private static int random()
        {
            return Cryptographer.cRandom.Next(1, 9);
        }

        /// <summary>
        /// 32桁のIV文字列を生成します。
        /// </summary>
        public static string GenerateIVString()
        {
            string text = string.Empty;
            for (int i = 0; i < 32; i++)
            {
                text += string.Format("{0}", Cryptographer.random());
            }
            return text;
        }

        public static string GenerateKeyString()
        {
            string text = string.Empty;
            for (int i = 0; i < 32; i++)
            {
                text += string.Format("{0:x}", Cryptographer.cRandom.Next(0, 65535));
            }
            string text2 = Convert.ToBase64String(Encoding.ASCII.GetBytes(text.ToString()));
            return text2.Substring(0, 32);
        }

        public static string ComputeSHA1(string input)
        {
            if (string.IsNullOrEmpty(input))
            {
                return null;
            }
            SHA1CryptoServiceProvider sHA1CryptoServiceProvider = new SHA1CryptoServiceProvider();
            byte[] bytes = Encoding.UTF8.GetBytes(input);
            byte[] array = sHA1CryptoServiceProvider.ComputeHash(bytes);
            string text = string.Empty;
            byte[] array2 = array;
            for (int i = 0; i < array2.Length; i++)
            {
                byte b = array2[i];
                text += string.Format("{0:x2}", b);
            }
            return text;
        }

        public static string ComputeMD5(string input)
        {
            MD5CryptoServiceProvider mD5CryptoServiceProvider = new MD5CryptoServiceProvider();
            byte[] bytes = Encoding.UTF8.GetBytes(input + MD5Key);
            byte[] array = mD5CryptoServiceProvider.ComputeHash(bytes);
            string text = string.Empty;
            byte[] array2 = array;
            for (int i = 0; i < array2.Length; i++)
            {
                byte b = array2[i];
                text += b.ToString("x2");
            }
            return text;
        }

        public static string EncryptAES(string message, string IVString)
        {
            byte[] bytes = Encoding.UTF8.GetBytes(Cryptographer.AESKey);
            byte[] bytes2 = Encoding.UTF8.GetBytes(IVString);
            string result = null;
            RijndaelManaged rijndaelManaged = new RijndaelManaged();
            rijndaelManaged.BlockSize = 256;
            rijndaelManaged.Key = bytes;
            rijndaelManaged.IV = bytes2;
            rijndaelManaged.Mode = CipherMode.CBC;
            rijndaelManaged.Padding = PaddingMode.Zeros;
            try
            {
                MemoryStream memoryStream = new MemoryStream();
                using (CryptoStream cryptoStream = new CryptoStream(memoryStream, rijndaelManaged.CreateEncryptor(bytes, bytes2), CryptoStreamMode.Write))
                {
                    using (StreamWriter streamWriter = new StreamWriter(cryptoStream))
                    {
                        streamWriter.Write(message);
                        streamWriter.Close();
                    }
                    cryptoStream.Close();
                }
                byte[] inArray = memoryStream.ToArray();
                result = Convert.ToBase64String(inArray);
                memoryStream.Close();
            }
            catch (Exception ex)
            {
                Console.WriteLine("An error occurred: {0}", ex.Message);
            }
            finally
            {
                rijndaelManaged.Clear();
            }
            return result;
        }

        public static string DecryptAES(string encstr, string IVString)
        {
            byte[] bytes = Encoding.UTF8.GetBytes(Cryptographer.AESKey);
            byte[] bytes2 = Encoding.UTF8.GetBytes(IVString);
            RijndaelManaged rijndaelManaged = new RijndaelManaged();
            rijndaelManaged.BlockSize = 256;
            rijndaelManaged.Key = bytes;
            rijndaelManaged.IV = bytes2;
            rijndaelManaged.Mode = CipherMode.CBC;
            rijndaelManaged.Padding = PaddingMode.Zeros;
            byte[] buffer = Convert.FromBase64String(encstr);
            string result = string.Empty;
            try
            {
                using (MemoryStream memoryStream = new MemoryStream(buffer))
                {
                    using (CryptoStream cryptoStream = new CryptoStream(memoryStream, rijndaelManaged.CreateDecryptor(bytes, bytes2), CryptoStreamMode.Read))
                    {
                        using (StreamReader streamReader = new StreamReader(cryptoStream))
                        {
                            result = streamReader.ReadToEnd();
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("An error occurred: {0}", ex.Message);
            }
            finally
            {
                rijndaelManaged.Clear();
            }
            return result;
        }

        public static string EncryptRJ256(string prm_text_to_encrypt)
        {
            RijndaelManaged rijndaelManaged = new RijndaelManaged();
            rijndaelManaged.Padding = PaddingMode.Zeros;
            rijndaelManaged.Mode = CipherMode.CBC;
            rijndaelManaged.KeySize = 256;
            rijndaelManaged.BlockSize = 256;
            byte[] array = new byte[0];
            byte[] rgbIV = new byte[0];
            string s = Cryptographer.GenerateKeyString();
            string s2 = Cryptographer.RJ256Key;
            array = Encoding.UTF8.GetBytes(s);
            rgbIV = Encoding.UTF8.GetBytes(s2);
            ICryptoTransform transform = rijndaelManaged.CreateEncryptor(array, rgbIV);
            MemoryStream memoryStream = new MemoryStream();
            CryptoStream cryptoStream = new CryptoStream(memoryStream, transform, CryptoStreamMode.Write);
            byte[] bytes = Encoding.UTF8.GetBytes(prm_text_to_encrypt);
            cryptoStream.Write(bytes, 0, bytes.Length);
            cryptoStream.FlushFinalBlock();
            byte[] array2 = memoryStream.ToArray();
            byte[] array3 = new byte[array2.Length + array.Length];
            Array.Copy(array2, 0, array3, 0, array2.Length);
            Array.Copy(array, 0, array3, array2.Length, array.Length);
            return Convert.ToBase64String(array3);
        }

        public static string DecryptRJ256(string prm_text_to_decrypt)
        {
            byte[] array = Convert.FromBase64String(prm_text_to_decrypt);
            RijndaelManaged rijndaelManaged = new RijndaelManaged();
            rijndaelManaged.Padding = PaddingMode.Zeros;
            rijndaelManaged.Mode = CipherMode.CBC;
            rijndaelManaged.KeySize = 256;
            rijndaelManaged.BlockSize = 256;
            byte[] array2 = new byte[32];
            byte[] rgbIV = new byte[32];
            byte[] array3 = new byte[array.Length - array2.Length];
            Array.Copy(array, 0, array3, 0, array3.Length);
            Array.Copy(array, array.Length - array2.Length, array2, 0, array2.Length);
            rgbIV = Encoding.UTF8.GetBytes(RJ256Key);
            ICryptoTransform transform = rijndaelManaged.CreateDecryptor(array2, rgbIV);
            byte[] array4 = new byte[array3.Length];
            MemoryStream stream = new MemoryStream(array3);
            CryptoStream cryptoStream = new CryptoStream(stream, transform, CryptoStreamMode.Read);
            cryptoStream.Read(array4, 0, array4.Length);
            return Encoding.UTF8.GetString(array4).TrimEnd(new char[1]);
        }
    }
}
