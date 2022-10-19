package io.github.brelee2222.NeuralNetwork;

import io.github.brelee2222.NeuralNetwork.Network.NeuralNetwork;
import io.github.brelee2222.NeuralNetwork.Neuron.Neuron;
import io.github.brelee2222.NeuralNetwork.Neuron.NeuronInput;

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
                12
                + network.neuronLayers.length*4;
        for(int i = 0; i != network.neuronLayers.length; i++) for(Neuron neuron : network.neuronLayers[i])
            size += neuron.inputs.length*8;

        byte[] data = new byte[size];
        int index = 0;
        for(Neuron[] layer : network.neuronLayers) for(Neuron neuron : layer) for(NeuronInput input : neuron.inputs)
            index = writeLong(Double.doubleToLongBits(input.getWeight()), data, index);
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

    public static int writeLong(long value, byte[] data, int index) {
        return writeInt((int) value, data, writeInt((int) (value >> 32), data, index));
    }
}
