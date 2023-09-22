//
//  ContentView.swift
//  astrobirthdayios
//
//  Created by Sasha Bakanov on 03/08/2023.
//

import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        Text(Greeting().greet())
            .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
