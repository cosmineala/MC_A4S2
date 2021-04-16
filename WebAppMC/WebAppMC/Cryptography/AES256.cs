using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace WebAppMC.Cryptography
{
    public class AES256
    {

        AesManaged aes = new AesManaged();

        public string NewKey()
        {
            aes.KeySize = 256;
            aes.GenerateKey();
            var key = aes.Key;

            byte[] nkey = new byte[16];

            AesGcm aesgcm = new AesGcm(nkey);

            //string output = "";

            //foreach( var item in key)
            //{
            //    output += item.ToString();
            //}
            //return output;



            return Convert.ToBase64String(key);
        }

        public static implicit operator ValueTask(AES256 v)
        {
            throw new NotImplementedException();
        }
    }
}
