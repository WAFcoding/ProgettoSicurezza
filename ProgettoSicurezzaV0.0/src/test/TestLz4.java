package test;

/*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import java.io.UnsupportedEncodingException;

import org.bouncycastle.util.Arrays;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;
import net.jpountz.lz4.LZ4SafeDecompressor;

public class TestLz4 {

  public static void main(String[] args) throws Exception {
    example();
  }

  private static void example() throws UnsupportedEncodingException {
    LZ4Factory factory = LZ4Factory.fastestInstance();

    byte[] data = "1234534777777777778885234572".getBytes("UTF-8");
    final int decompressedLength = data.length;

    // compress data
    LZ4Compressor compressor = factory.fastCompressor();
    int maxCompressedLength = compressor.maxCompressedLength(decompressedLength);
    byte[] compressed = new byte[maxCompressedLength];
    int compressedLength = compressor.compress(data, 0, decompressedLength, compressed, 0, maxCompressedLength);

    System.out.println(maxCompressedLength);
    System.out.println(compressed.length);
    System.out.println(new String(data) + "\nl_noncompressa="+data.length);
    compressed = Arrays.copyOf(compressed, compressedLength);
    System.out.println(new String(compressed) +"\nl_compressa="+ compressed.length);
    // decompress data
    // - method 1: when the decompressed length is known
    LZ4FastDecompressor decompressor = factory.fastDecompressor();
    byte[] restored = new byte[decompressedLength];
    int compressedLength2 = decompressor.decompress(compressed, 0, restored, 0, decompressedLength);
    // compressedLength == compressedLength2
    System.out.println(new String(restored));

    // - method 2: when the compressed length is known (a little slower)
    // the destination buffer needs to be over-sized
    LZ4SafeDecompressor decompressor2 = factory.safeDecompressor();
    int decompressedLength2 = decompressor2.decompress(compressed, 0, compressedLength, restored, 0);
    // decompressedLength == decompressedLength2
  }

}