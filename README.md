# Warehouse Server System

Multi-threaded client–server system with a shared warehouse state (apples/oranges).
Clients connect via TCP sockets; mutual exclusion ensures safe concurrent access.

## Run
1. `java WarehouseServer` (starts on port 4545)
2. In separate terminals: `java CustomerAClient`, `java CustomerBClient`, `java SupplierClient`
3. Customer Client Commands: `check_stock`, `buy_apples N`, `buy_oranges N`
4. Supplier Client Commands: `check_stock`,  `add_apples N`, `add_oranges N`

## Classes
- `WarehouseServer` – listens on a ServerSocket and spawns `WarehouseServerThread` per client connection, passing the shared state containing the shared resource.
  
- `WarehouseServerThread` – per-client logic. Carries out the additional handshake to aquire client type and ID. Facilitate the read–process–respond loop with locking around state access.
  
- `SharedWarehouseState` – Contains the shared variables. Processes requests from the client to correctly modify the shared variables. Houses the locking logic.
  
- `CustomerAClient` / `CustomerBClient` – thin clients for purchases and stock checks.

- `SupplierClient` – thin client for restocking and stock checks.
# CS3004-Network-Computing---Assessment-Coursework-for-2025-26
