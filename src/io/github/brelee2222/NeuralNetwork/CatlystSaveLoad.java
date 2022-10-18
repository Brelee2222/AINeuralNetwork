package io.github.brelee2222.NeuralNetwork;

import io.github.brelee2222.NeuralNetwork.Network.NeuralNetwork;

public class CatlystSaveLoad {
    /**
     * Save format
     * 1. inputs (int)
     * 2. layers (int)
     * 3. layerSize (int) * layers
     * 4. Neuron inputs (double)
     */

    public static void save(NeuralNetwork network) {
        int size =
                8
                + network.neuronLayers.length*4
                + network.inputs*8;
        for(int i = 1; i+1 != network.neuronLayers.length; i++)
            size += network.neuronLayers[i].length*8;

        byte[] data = new byte[size];
        int index = 0;


    }

    public static int writeByte(byte value, byte[] data, int index) {
        data[index++] = value;
        return index;
    }

    public static int writeShort(short value, byte[] data, int index) {
        return writeByte((byte) (value & 0xff), data, writeByte((byte) ((value >> 8) & 0xff), data, index));
    }

    public static int writeInt(int value, byte[] data, int index) {
        return writeShort((short) (value & 0xffff), data, writeShort((short) ((value >> 16) & 0xffff), data, index));
    }
}
